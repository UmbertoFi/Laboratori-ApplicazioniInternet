import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

import {Prenotazione, User} from '../_models';
import {Observable} from 'rxjs';
import {Linea} from '../linea';

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

  // login setItem
  login(data: any) {
    // console.log(data.username);
    // console.log(data.token);
    localStorage.setItem('access_token',data.token);
  }

  getlinee(): Observable<Linea[]>{
    return this.http.get<Linea[]>(`http://localhost:8080/lines`);
  }

  inserisciPrenotazione(p : Prenotazione, ): Observable<Prenotazione>{
    let url = 'http://localhost:8080/'+
    return this.http.post<Prenotazione>(url);
  }


}
