import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

import {Prenotazione, User} from '../_models';
import {Observable} from 'rxjs';
import {Linea} from '../linea';
import {checkUsername} from '../checkUsername';
import {Bambino} from '../bambino';
import {NUOVAPrenotazione} from '../nuovaprenotazione';

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

  inserisciPrenotazione(pren : Prenotazione, linea: string, data: string): Observable<Prenotazione>{
    let url = 'http://localhost:8080/reservations/'+linea+'/'+data;
    console.log(pren.Persona+' '+pren.verso+' '+pren.fermata);
    return this.http.post<Prenotazione>(url, pren, this.httpOptions);
  }


  notPresent(controlName: string): Observable<checkUsername>{
    let checkUsernameResponse = this.http.get<checkUsername>(`http://localhost:8080/utility/checkUsername/`+controlName);
    return checkUsernameResponse;
  }

  getbambini() {
    return this.http.get<Bambino[]>('http://localhost:8080/utility/children');
  }

  inserisciPrenotazioneRitardata(id_bambino: number, linea: string, id_fermata: number, verso: number, data: string) {
    let url = 'http://localhost:8080/utility/reservations/'+linea+'/'+data;
    // console.log(pren.Persona+' '+pren.verso+' '+pren.fermata);
    let versoChar;
    if(verso==0)
      versoChar='A';
    else
      versoChar='R';
    console.log(versoChar);
    return this.http.post<Prenotazione>(url, new NUOVAPrenotazione(id_bambino,id_fermata,versoChar), this.httpOptions);
  }
}
