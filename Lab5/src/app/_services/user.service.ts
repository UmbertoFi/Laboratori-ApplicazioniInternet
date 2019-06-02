import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

import { User } from '../_models';
import {Observable} from 'rxjs';

@Injectable()
export class UserService {
  constructor(private http: HttpClient) {}

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };


  register(user: User): Observable<User> {
    console.log(user);
    return this.http.post<User>(`http://localhost:8080/register`, user, this.httpOptions);
  }
}
