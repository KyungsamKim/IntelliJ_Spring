import React from "react";
// import { GoogleLogin } from "react-google-login";
import Axios from "axios";
import { GoogleLogin } from '@react-oauth/google';
import { GoogleOAuthProvider } from '@react-oauth/google';
const config = {
  headers: {
    "Content-Type": "application/json; charset=utf-8",
  },
};

const responseGoogle = async (response) => {
  console.log(1, response);
  let jwtToken = await Axios.post(
    "http://localhost:8080/oauth/jwt/google",
    JSON.stringify(response),
    config
  );
  console.log("jwtToken: "+jwtToken);
  if (jwtToken.status === 200) {
    console.log(2, jwtToken.data);
    localStorage.setItem("jwtToken", jwtToken.data);
  }
};

const Login = () => {
  return (
    <GoogleOAuthProvider clientId='939296769148-66e0r5onh9ppb2g9fpt65ps1pvmqj7gg.apps.googleusercontent.com'>
      <GoogleLogin
            onSuccess={responseGoogle}
            onError={() => {
              console.log('Login Failed');
            }}
      />
    </GoogleOAuthProvider>
  );
};

export default Login;