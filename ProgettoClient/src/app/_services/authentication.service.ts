import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { map } from 'rxjs/operators';
import {UserLogin} from '../_models/userLogin';

@Injectable()
export class AuthenticationService {
  constructor(private http: HttpClient) { }

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  login(user: UserLogin) {
    function criptaPassword(password: string) {
      let c;
      let encryptedPassword: string = "";

      for(let i=0; i<password.length; i++){
        c = password.charCodeAt(i);
        if(c==97 || c==98 || c==99 || c==100 || c==101){
          c++;
          encryptedPassword += String.fromCharCode(c);
        } else if(c==102 || c==103 || c==104 || c==105 || c==106){
          c += 3;
          encryptedPassword += String.fromCharCode(c);
        } else if(c==102 || c==103 || c==104 || c==105 || c==106){
          c += 8;
          encryptedPassword += String.fromCharCode(c);
        } else if(c==102 || c==103 || c==104 || c==105 || c==106){
          c += 2;
          encryptedPassword += String.fromCharCode(c);
        } else {
          c += 7;
          encryptedPassword += String.fromCharCode(c);
        }
      }
      return encryptedPassword;
    }
    user.password=criptaPassword(user.password);
    return this.http.post<UserLogin>('http://localhost:8080/login', user, this.httpOptions);
  }

  logout() {
    // remove user from local storage to log user out
    localStorage.removeItem('access_token');
    localStorage.removeItem('username');
  }
}
