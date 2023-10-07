### CIBA Sample App

A sample CIBA app to test with Keycloak CIBA authentication flow.

#### Run

To run it locally, you just need to run below command using `npm`:

```
npm install
npm start
```

It will start server on port `3001`.

#### Verify

To verify `curl` can be used:

curl -X POST http://localhost:3001/request

It will result as `created`

#### Usage

This sample app has just one POST endpoint which will be consumed by Keycloak when CIBA request received.
It will print the request body and header which has CIBA information which can be used to perform CIBA authentication and then callback on Keycloak can be perform
to nofify if the CIBA request is `SUCCEED`, `UNAUTHORIZED` & `CANCELLED`.

There are different ways to configure CIBA Auth Server in Keycloak, most common is Keycloak startup args.

Example:

```
--spi-ciba-auth-channel-ciba-http-auth-channel-http-authentication-channel-uri=http://localhost:3001/request
```

The hostname is `localhost` here as running on local, but if you use any separate host to deploy it you can use it accordingly.
