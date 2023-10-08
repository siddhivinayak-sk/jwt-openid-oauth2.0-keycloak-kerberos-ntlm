### CIBA Sample App

A sample CIBA app to test with Keycloak CIBA authentication flow.

#### Run

To run it locally, you just need to run below command using `npm`:

```
npm install
npm start
```

If you want to run with docker:

```
docker build -f Dockerfile -t siddhivinayaksk/ciba-sample-app:latest .
docker push siddhivinayaksk/ciba-sample-app:latest  # already pushed
docker run -p 3001:3001 siddhivinayaksk/ciba-sample-app:latest
```

It will start server on port `3001`.

#### Verify

To verify `curl` can be used:

curl -X POST http://localhost:3001/request

It will result as `created`

#### Usage

This sample app has just two endpoints:

1. `/request`: This will be consumed by Keycloak when CIBA request received.
It will print the request body and header which has CIBA information which can be used to perform CIBA authentication and then callback on Keycloak can be perform
to nofify if the CIBA request is `SUCCEED`, `UNAUTHORIZED` & `CANCELLED`.


2. `/response/<request id>/<transaction status>`: This endpoint is used to callback Keycloak about the decision taken by CIBA Auth server. The possibility 
of trasaction status could be `SUCCEED`, `UNAUTHORIZED` & `CANCELLED`.

It takes two path variables:
a. `request id`: It is printed on the console of CIVA sample app and used to refer the Keycloak request came to this server.
b. `transaction status`: This is the status provided to callback.

s => SUCCEED
u => UNAUTHORIZED
c => CANCELLED

Example:

```
curl http://localhost:3001/response/1/s
```

#### Configure CIBA Sample App in Keycloak

There are different ways to configure CIBA Auth Server in Keycloak, most common is Keycloak startup args.

Example:

```
--spi-ciba-auth-channel-ciba-http-auth-channel-http-authentication-channel-uri=http://localhost:3001/request
```

The hostname is `localhost` here as running on local, but if you use any separate host to deploy it you can use it accordingly.
