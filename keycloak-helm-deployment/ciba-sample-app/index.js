const express = require('express');
const app = express();
app.use(express.json());
app.use(express.urlencoded({ extended: false }));

app.post('/request',async(req,res) => {
  console.log('===========================Request Start=========================');
  console.log('=====Request Body========');
  console.log(req.body);
  console.log('=====Request Headers=====');
  console.log(req.headers);  
  console.log('===========================Request End===========================');
  res.sendStatus(201);
})

const port = 3001;

app.listen(port, () => console.log('Server running on port ${port}'));
