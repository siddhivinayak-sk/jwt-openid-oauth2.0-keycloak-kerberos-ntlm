https://github.com/bitnami/charts/tree/main/bitnami/keycloak/#installing-the-chart

h install keyclaok-x oci://registry-1.docker.io/bitnamicharts/keycloak
h install keyclaok-x --set auth.adminPassword=secretpassword oci://registry-1.docker.io/bitnamicharts/keycloak
h uninstall keyclaok-x

Url: http://keycloak.acg-digistore.com
user/secretpassword

or

Url: https://keycloak.acg-digistore.com
user/secretpassword

It may create issue while exposing Keycloak on HTTP/S via Bitnami Helm & Istio, you can try below workarounds base on bundle version:
a. Set env variables:
PROXY_ADDRESS_FORWARDING=true
KEYCLOAK_PROXY_ADDRESS_FORWARDING=true
KEYCLOAK_FRONTEND_URL=https://keycloak.acg-digistore.com
KEYCLOAK_EXTRA_ARGS="-Dkeycloak.frontendUrl=https://keycloak.acg-digistore.com"
KC_PROXY=passthrough
KEYCLOAK_PROXY=passthrough
b. manually set frontend url:
Keycloak Web Console -> Realm Setting -> Frontend URL = https://keycloak.acg-digistore.com/
and set allowed origin is * in client's setting


A. Create a Realm skent
B. Create a client in skent realm by enabling all auth flows
c. Create a user named test1

1. Standard flow with auth code
a. Auth Request:
http://keycloak.acg-digistore.com/realms/skent/protocol/openid-connect/auth?client_id=app1&response_mode=fragment&response_type=code&scope=openid&redirect_uri=https://www.google.com

It will redirect to login page, provide username and password then do token request.

b. Token Request:
http://keycloak.acg-digistore.com/realms/skent/protocol/openid-connect/token
x-www-formurlencoded body:
client_id:app1
redirect_uri:https://www.google.com
grant_type:authorization_code
code:452795fc-3987-4388-b17e-464884fae7d0.dfb68dd2-2804-40d9-afc4-409bca0203c1.547db448-2012-442c-a24b-1c73effce7c7
client_secret:8WQDHQ9X4mdtzK1Kp5gz833T4dHx0cbH

2. Direct access grants
It will get token directly for the user:
http://keycloak.acg-digistore.com/realms/skent/protocol/openid-connect/token
x-www-formurlencoded body:
client_id:app1
client_secret:8WQDHQ9X4mdtzK1Kp5gz833T4dHx0cbH
grant_type:password
username:test1
password:test1

3. Implicit flow
It will get token directly for the user:
http://keycloak.acg-digistore.com/realms/skent/protocol/openid-connect/auth?client_id=app1&response_mode=fragment&response_type=token&scope=openid&redirect_uri=https://www.google.com

It will directly return token

4. Service accounts roles
It will get token directly for client:
http://keycloak.acg-digistore.com/realms/skent/protocol/openid-connect/token
x-www-formurlencoded body:
client_id:app1
client_secret:8WQDHQ9X4mdtzK1Kp5gz833T4dHx0cbH
grant_type:client_credentials

5. OAuth 2.0 Device Authorization Grant
a. Initiate device authentication
http://keycloak.acg-digistore.com/realms/skent/protocol/openid-connect/auth/device
client_id:app1
client_secret:8WQDHQ9X4mdtzK1Kp5gz833T4dHx0cbH

b. It will return a device code and URL to authenticate user. Use URL to authenticate and provide authorization for device.
c. Token request
http://keycloak.acg-digistore.com/realms/skent/protocol/openid-connect/token
x-www-formurlencoded body:
client_id:app1
client_secret:8WQDHQ9X4mdtzK1Kp5gz833T4dHx0cbH
grant_type:urn:ietf:params:oauth:grant-type:device_code
device_code:iNBSlPUw4CCxSiaS6G0hoTbgsVYVMzxQTuoMLIANv2Y

6. OIDC CIBA Grant
Run CIBA Server (a server which takes request from Keyclaok for approval) and configure it in Keycloak
a. checkout code from: https://github.com/grootan/CIBA-Sample-App
b. run the app
c. configure keycloak: 
Manual way (if keycloak running conventionally):
<spi name="ciba-auth-channel">
  <default-provider>ciba-http-auth-channel</default-provider>
  <provider name="ciba-http-auth-channel" enabled="true">
    <properties>
      <property name="httpAuthenticationChannelUri" value="http://localhost:3001/request"/>
    </properties>
  </provider>
</spi>

Add "KEYCLOAK_EXTRA_ARGS" with values:
--spi-ciba-auth-channel-ciba-http-auth-channel-http-authentication-channel-uri=http://host.docker.internal:3001/request
--spi-ciba-auth-channel-ciba-http-auth-channel-http-authentication-channel-uri=http://ciba-svc/request
With helm bundle, extraStartupArgs can be set with values.yml


a. Initiate CIBA auth
http://keycloak.acg-digistore.com/realms/skent/protocol/openid-connect/ext/ciba/auth
x-www-formurlencoded body:
client_id:app1
client_secret:8WQDHQ9X4mdtzK1Kp5gz833T4dHx0cbH
scope:openid
login_hint:test1
binding_message:binding_message

b. It will create a request of coinfigured CIBA auth with details to provide authentication
like bearer token in authentication header which is used to make callback request

And return auth_req_id:
{
    "auth_req_id": "eyJhbGciOi...",
    "expires_in": 120,
    "interval": 5
}

c. Poll for token request:
http://keycloak.acg-digistore.com/realms/skent/protocol/openid-connect/token
x-www-formurlencoded body:
client_id:app1
client_secret:8WQDHQ9X4mdtzK1Kp5gz833T4dHx0cbH
grant_type:urn:openid:params:grant-type:ciba
auth_req_id:

d. It will return to wait for authentication.
{
    "error": "authorization_pending",
    "error_description": "The authorization request is still pending as the end-user hasn't yet been authenticated."
}

e. Callback request to confirm about CIBA auth request
http://keycloak.acg-digistore.com/realms/skent/protocol/openid-connect/ext/ciba/auth/callback
headers:
Authorization:Bearer 
Content-Type:application/json
body:
{"status" : "SUCCEED"}

Other values could be: UNAUTHORIZED, CANCELLED
It will return like:
{
    "type": "application",
    "subtype": "json",
    "parameters": {},
    "wildcardType": false,
    "wildcardSubtype": false
}


f. Token request again (same as Poll request at step c.)
http://keycloak.acg-digistore.com/realms/skent/protocol/openid-connect/token
x-www-formurlencoded body:
client_id:app1
client_secret:8WQDHQ9X4mdtzK1Kp5gz833T4dHx0cbH
grant_type:urn:openid:params:grant-type:ciba
auth_req_id:
