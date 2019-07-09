import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

import {Prenotazione, User} from '../_models';
import {Observable} from 'rxjs';
import {checkUsername} from '../_models/checkUsername';
import {Bambino} from '../_models/bambino';
import {NUOVAPrenotazione} from '../_models/nuovaprenotazione';
import {BambinoNew} from '../_models/bambinoNew';
import {CandidaturaAccompagnatore} from '../_models/candidaturaAccompagnatore';

@Injectable()
export class UserService {
  constructor(private http: HttpClient) {}

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*'
    })
  };


  register(user: User): Observable<User> {
    return this.http.post<User>(`http://localhost:8080/register`, user, this.httpOptions);
  }

  // login setItem
  login(data: any) {
    localStorage.setItem('access_token',data.token);
    localStorage.setItem('username',data.username);
  }

  /* inserisciPrenotazione(pren : Prenotazione, linea: string, data: string): Observable<Prenotazione>{
    let url = 'http://localhost:8080/reservations/'+linea+'/'+data;
    return this.http.post<Prenotazione>(url, pren, this.httpOptions);
  } */


  notPresent(controlName: string): Observable<checkUsername>{
    let checkUsernameResponse = this.http.get<checkUsername>(`http://localhost:8080/utility/checkUsername/`+controlName);
    return checkUsernameResponse;
  }

  aggiungiBambino(bambino: BambinoNew) {
    return this.http.post<BambinoNew>('http://localhost:8080/utility/children/'+localStorage.getItem('username'),bambino,this.httpOptions);
  }

  candidatiAccompagnatore(candidaturaAccompagnatore: CandidaturaAccompagnatore) {
    return this.http.post<CandidaturaAccompagnatore>('http://localhost:8080/utility/available/'+localStorage.getItem('username'),candidaturaAccompagnatore,this.httpOptions)
  }
}
