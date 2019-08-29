import {Injectable} from '@angular/core';
import {Bambino, NUOVAPrenotazione} from '../_models';
import {HttpClient, HttpHeaders} from '@angular/common/http';

@Injectable()
export class LineaService {
  constructor(private http: HttpClient) {}

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*',
      'Authorization': 'Bearer '+localStorage.getItem('access_token')
    })
  };

  getbambini() {
    return this.http.get<Bambino[]>('http://localhost:8080/utility/children', this.httpOptions);
  }

  inserisciPrenotazioneRitardata(id_bambino: number, linea: string, id_fermata: number, verso: number, data: string) {
    let url = 'http://localhost:8080/utility/reservations/'+linea+'/'+data;
    let versoChar;
    if(verso==0)
      versoChar='A';
    else
      versoChar='R';
    return this.http.post<NUOVAPrenotazione>(url, new NUOVAPrenotazione(id_bambino,id_fermata,versoChar), this.httpOptions);
  }

  updateprenotazione(id_bambino: number, id_fermata: number, data: string, verso: number) {
    let url = 'http://localhost:8080/utility/reservations/'+data;
    let versoChar;
    if(verso==0)
      versoChar='A';
    else
      versoChar='R';
    return this.http.put<NUOVAPrenotazione>(url, new NUOVAPrenotazione(id_bambino,id_fermata,versoChar), this.httpOptions);
  }

  inserisciPrenotazioneRitardataAccompagnatore(id_bambino: number, linea: string, id_fermata: number, verso: number, data: string) {
    let url = 'http://localhost:8080/utility/accompagnatore/reservations/'+linea+'/'+data;
    let versoChar;
    if(verso==0)
      versoChar='A';
    else
      versoChar='R';
    return this.http.post<NUOVAPrenotazione>(url, new NUOVAPrenotazione(id_bambino,id_fermata,versoChar), this.httpOptions);
  }


  updateprenotazioneAccompagnatore(id_bambino: number, id_fermata: number, data: string, verso: number) {
    let url = 'http://localhost:8080/utility/accompagnatore/reservations/'+data;
    let versoChar;
    if(verso==0)
      versoChar='A';
    else
      versoChar='R';
    return this.http.put<NUOVAPrenotazione>(url, new NUOVAPrenotazione(id_bambino,id_fermata,versoChar), this.httpOptions);
  }
}
