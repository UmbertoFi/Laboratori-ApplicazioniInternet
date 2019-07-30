import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

import {Prenotazione, User} from '../_models';
import {Observable} from 'rxjs';
import {checkUsername} from '../_models/checkUsername';
import {Bambino} from '../_models/bambino';
import {NUOVAPrenotazione} from '../_models/nuovaprenotazione';
import {BambinoNew} from '../_models/bambinoNew';
import {CandidaturaAccompagnatore} from '../_models/candidaturaAccompagnatore';
import {ModificaRuolo} from '../_models/modificaRuolo';
import {TrovaDisponibilita} from '../_models/trovaDisponibilita';
import {Disponibilita} from '../_models/disponibilita';
import {UtenteNew} from '../_models/utenteNew';
import {presaVisione} from '../_models/presaVisione';

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

    /* function criptaPassword(password: string) {
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
    user.confirmPassword=criptaPassword(user.confirmPassword); */
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

  modificaRuolo(modificaRuolo: ModificaRuolo) {
    return this.http.put<ModificaRuolo>('http://localhost:8080/utility/modificaRuolo',modificaRuolo,this.httpOptions);
  }

  trovaDisponibilita(trovaDisponibilita: TrovaDisponibilita) {
    return this.http.get<Disponibilita>('http://localhost:8080/utility/disponibilita/'+trovaDisponibilita.linea+"/"+trovaDisponibilita.data+"/"+trovaDisponibilita.verso,this.httpOptions);
  }

  consolidaTurno(disponibilita: Disponibilita) {
    return this.http.post<Disponibilita>('http://localhost:8080/utility/turno', disponibilita, this.httpOptions)
  }

  azzeraNotifica(username: String){
    return this.http.post<String>('http://localhost:8080/utility/zero/'+username, this.httpOptions)
  }

  inizializzaNotifica(username: String){
    return this.http.get<number>('http://localhost:8080/utility/primovalorenotifiche/'+username, this.httpOptions);
  }

  presaVisione(p: presaVisione) {
    return this.http.put<presaVisione>('http://localhost:8080/utility/confirm/turno', p, this.httpOptions);
  }

  rimuoviPrenotazione(linea: string, data: string, resID: string) {
    console.log('http://localhost:8080/reservations/'+linea+'/'+data+'/'+resID);
    return this.http.delete('http://localhost:8080/reservations/'+linea+'/'+data+'/'+resID, this.httpOptions);
  }
}
