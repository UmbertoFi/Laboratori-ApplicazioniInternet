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
    return this.http.post<UserLogin>('http://localhost:8080/login', user, this.httpOptions);
  }

  logout() {
    // remove user from local storage to log user out
    localStorage.removeItem('access_token');
  }
}
