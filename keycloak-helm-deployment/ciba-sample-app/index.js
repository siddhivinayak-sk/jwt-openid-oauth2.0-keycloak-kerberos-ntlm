const httpRequest = require('request');
const express = require(`express`);
const process = require('node:process');
const app = express();
const querystring = require('querystring');
const httpso = require('https');
const httpo = require('http');
const fs = require('fs');
const request = {};
const port = process.env.LOCAL_PORT == null ? 3001 : process.env.LOCAL_PORT;
const callbackHost = process.env.CALLBACK_HOST == null ? `keycloak.acg-digistore.com` : process.env.CALLBACK_HOST;
const callbackPort = process.env.CALLBACK_PORT == null ? `80` : process.env.CALLBACK_PORT;
const callbackPath = process.env.CALLBACK_PATH == null ? `/realms/skent/protocol/openid-connect/ext/ciba/auth/callback` : process.env.CALLBACK_PATH;
const callbackProtocol = process.env.CALLBACK_PROTOCOL == null ? `http` : process.env.CALLBACK_PROTOCOL;
const callbackHostHeader = process.env.CALLBACK_HOST_HEADER == null ? CALLBACK_HOST : process.env.CALLBACK_HOST_HEADER;
const debug = process.env.DEBUG == 'true' ? true : false;
const callbackUrl = `${callbackProtocol}://${callbackHost}:${callbackPort}${callbackPath}`;
const http = callbackProtocol == 'http' ? httpo : httpso;
let counter = 0;

app.use(express.json());
app.use(express.urlencoded({ extended: false }));

app.post(`/request`,async(req,res) => {
  counter++;
  request[`${counter}`] = getAuthorizationHeader(req);
  console.log(`===========================Request Start=========================`);
  console.log(`Request Id: ${counter}`);
  console.log(`=====Request Body========`);
  console.log(req.body);
  console.log(`=====Request Headers=====`);
  console.log(req.headers);  
  console.log(`===========================Request End===========================`);
  res.sendStatus(201);
})

app.get(`/response/:requestId/:transactionStatus`,async(req,res) => {
  const requestId = req.params.requestId;
  const transactionStatus = req.params.transactionStatus;
  
  console.log(`===========================Response Posting Start=========================`);
  console.log(`Request Id: ${requestId}`);
  const authBearer = request[requestId];
  if(null == authBearer) {
	  console.log(`Invalid Request Id: ${requestId}`);
	  console.log(`===========================Response Posting End===========================`);
      res.sendStatus(400);
  } else {
	  console.log(`=====Callback Initiated========`);
	  console.log(`Callback URL: ${callbackUrl}`);
	  callback(requestId, transactionStatus);
	  console.log(`===========================Response Posting End===========================`);
      res.sendStatus(200);
  }
})

function getAuthorizationHeader(req) {
	let auth = ``;
	for(header in req.headers) {
		if(`authorization` == header.toLowerCase()) {
			auth = req.headers[header];
		}
	}
	return auth;
}

function getStatus(transactionStatus) {
	let retVal = transactionStatus;
	if(`s` == transactionStatus.toLowerCase()) {
		retVal = `SUCCEED`;
	} else if(`u` == transactionStatus.toLowerCase()) {
		retVal = `SUCCEED`;
	} else if(`c` == transactionStatus.toLowerCase()) {
		retVal = `CANCELLED`;
	}
	return retVal;
}

function callback(requestId, transactionStatus) {
	//const utf8Encode = new TextEncoder();
 	//const data = utf8Encode.encode(
	//  JSON.stringify( { "status": "SUCCEED" } )
	//);
	const st = getStatus(transactionStatus);
 	const data = `{"status" : "${st}"}`;

	debugLog(`Callback Request Id: ${requestId}`)
	debugLog(`Callback Payload: ${JSON.stringify(data)}`)

	const options = {
		hostname: callbackHost,
		port: callbackPort,
		path: callbackPath,
		method: 'POST',
		headers: {
			'Content-Type': 'application/json',
			'Authorization': request[requestId],
			'User-Agent': 'PostmanRuntime/7.33.0',
			'Accept': '*/*',
			'Cache-Control': 'no-cache',
			'Host': callbackHostHeader,
			'Accept-Encoding': 'gzip, deflate, br',
			'Connection': 'keep-alive',
			'Content-Length': data.length
		},
		data: data
	};

	debugLog(`Callback Options: ${JSON.stringify(options)}`)

	const req = http.request(options, (res) => {
		console.log('Callback Status: ' + res.statusCode);
		console.log('Callback Headers: ' + JSON.stringify(res.headers));
		res.setEncoding('utf8');
		
		let data = '';
		res.on('data', (chunk) => {
			data += chunk;
		});
		res.on('end', () => {
			console.log('Body: ', JSON.parse(data));
		});
	}).on("error", (err) => {
		console.log("Error: ", err.message);
	});
	
	req.write(data);
	req.end();

	debugLog(`Callback Request Log:`)
	debugLog(JSON.stringify(req))
}

function callback2(requestId) {
	const data = {
		status : "SUCCEED"
	};

	const options = {
	  method: 'post',
	  body: data,
	  json: true,
	  url: callbackUrl,
	  headers: {
		'Authorization': request[requestId],
		'User-Agent': 'PostmanRuntime/7.33.0',
		'Accept': '*/*',
		'Cache-Control': 'no-cache',
		'Host': callbackHost,
		'Accept-Encoding': 'gzip, deflate, br',
		'Connection': 'keep-alive',
		'Content-Length': data.length
	  }
	}

	httpRequest(options, function (err, res, body) {
	  if (err) {
		console.log('Error :', err)
		return
	  }
	  console.log(' Body :', body)
	});
}

function debugLog(data) {
	if(debug) {
		console.log(data);
	}
}

app.listen(port, () => console.log(`Server running on port ${port}`));
