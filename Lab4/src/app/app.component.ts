import {Component, OnInit} from '@angular/core';
import {PageEvent} from '@angular/material';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'Lab4';
  linee = [
    {
      nome: '12',
      corse: [
        {
          data: '2019/04/30',
          tratte: [
            {
              verso: 'andata',
              fermate: [{
                nome: 'Piazza Mellano',
                ora: '07.35',
                persone: [
                  {nome: 'Rebecca', selected: false},
                  {nome: 'Mario', selected: false},
                  {nome: 'Salvatore', selected: false},
                  {nome: 'Filippo', selected: false}
                ]
              },
                {
                  nome: 'Via Primo Alpini',
                  ora: '07.40',
                  persone: [
                    {nome: 'Giuseppe', selected: false},
                    {nome: 'Emma', selected: false}
                  ]
                },
                {
                  nome: 'Via Vigo',
                  ora: '07.50',
                  persone: [
                    {nome: 'Giacomo', selected: false},
                    {nome: 'Enzo', selected: false},
                    {nome: 'Maria', selected: false}
                  ]
                },
                {
                  nome: 'Piazza XXV Aprile',
                  ora: '07.55',
                  persone: [
                    {nome: 'Angelo', selected: false}
                  ]
                },
                {
                  nome: 'Scuola',
                  ora: '08.00',
                  persone: []
                }]
            },
            {
              verso: 'ritorno',
              fermate: [{
                nome: 'Scuola',
                ora: '13.30',
                persone: []
              },
                {
                  nome: 'Piazza XXV Aprile',
                  ora: '13.35',
                  persone: [
                    {nome: 'Angelo', selected: false}
                  ]
                },
                {
                  nome: 'Via Vigo',
                  ora: '13.40',
                  persone: [
                    {nome: 'Giacomo', selected: false},
                    {nome: 'Enzo', selected: false},
                    {nome: 'Maria', selected: false}
                  ]
                },
                {
                  nome: 'Via Primo Alpini',
                  ora: '13.45',
                  persone: [
                    {nome: 'Giuseppe', selected: false},
                    {nome: 'Emma', selected: false}
                  ]
                },
                {
                  nome: 'Piazza Mellano',
                  ora: '13.50',
                  persone: [
                    {nome: 'Rebecca', selected: false},
                    {nome: 'Mario', selected: false},
                    {nome: 'Salvatore', selected: false},
                    {nome: 'Filippo', selected: false}
                  ]
                }]
            }
          ]
        },
        {
          data: '2019/05/30',
          tratte: [
            {
              verso: 'andata',
              fermate: [{
                nome: 'Piazza Mellano',
                ora: '07.35',
                persone: [
                  {nome: 'Benedetta', selected: false},
                  {nome: 'Aurora', selected: false},
                  {nome: 'Chanel', selected: false},
                  {nome: 'Matteo', selected: false},
                  {nome: 'Sara', selected: false},
                  {nome: 'Simone', selected: false},
                  {nome: 'Claudia', selected: false}
                ]
              },
                {
                  nome: 'Via Primo Alpini',
                  ora: '07.40',
                  persone: [
                    {nome: 'Giacomo', selected: false},
                    {nome: 'Emma', selected: false}
                  ]
                },
                {
                  nome: 'Via Vigo',
                  ora: '07.50',
                  persone: [
                    {nome: 'Isabel', selected: false},
                    {nome: 'Mohammed', selected: false},
                    {nome: 'Iaia', selected: false}
                  ]
                },
                {
                  nome: 'Piazza XXV Aprile',
                  ora: '07.55',
                  persone: [
                    {nome: 'Shibo', selected: false},
                    {nome: 'Vittoria', selected: false}
                  ]
                },
                {
                  nome: 'Scuola',
                  ora: '08.00',
                  persone: []
                }]
            },
            {
              verso: 'ritorno',
              fermate: [{
                nome: 'Scuola',
                ora: '13.30',
                persone: []
              },
                {
                  nome: 'Piazza XXV Aprile',
                  ora: '13.35',
                  persone: [
                    {nome: 'Shibo', selected: false},
                    {nome: 'Vittoria', selected: false}
                  ]
                },
                {
                  nome: 'Via Vigo',
                  ora: '13.40',
                  persone: [
                    {nome: 'Isabel', selected: false},
                    {nome: 'Mohammed', selected: false},
                    {nome: 'Iaia', selected: false}
                  ]
                },
                {
                  nome: 'Via Primo Alpini',
                  ora: '13.45',
                  persone: [
                    {nome: 'Giacomo', selected: false},
                    {nome: 'Emma', selected: false}
                  ]
                },
                {
                  nome: 'Piazza Mellano',
                  ora: '13.50',
                  persone: [
                    {nome: 'Benedetta', selected: false},
                    {nome: 'Aurora', selected: false},
                    {nome: 'Chanel', selected: false},
                    {nome: 'Matteo', selected: false},
                    {nome: 'Sara', selected: false},
                    {nome: 'Simone', selected: false},
                    {nome: 'Claudia', selected: false}
                  ]
                }]
            }
          ]
        }
      ]
    },
    {
      nome: '58',
      corse: [
        {
          data: '2019/05/22',
          tratte: [
            {
              verso: 'andata',
              fermate: [{
                nome: 'Piazza Pitagora',
                ora: '07.40',
                persone: [
                  {nome: 'Maurizio', selected: false},
                  {nome: 'Mimmo', selected: false},
                  {nome: 'Laura', selected: false}
                ]
              },
                {
                  nome: 'Piazza Castello',
                  ora: '07.40',
                  persone: [
                    {nome: 'Carlo', selected: false},
                    {nome: 'Giovanni', selected: false},
                    {nome: 'Zaira', selected: false},
                  ]
                },
                {
                  nome: 'Via Milano',
                  ora: '07.45',
                  persone: [
                    {nome: 'Teresa', selected: false},
                    {nome: 'Francesco', selected: false},
                    {nome: 'Cristina', selected: false}
                  ]
                },
                {
                  nome: 'Via Vigone',
                  ora: '07.55',
                  persone: [
                    {nome: 'Shibo', selected: false},
                    {nome: 'Vittoria', selected: false},
                    {nome: 'Tito', selected: false},
                    {nome: 'Tiberio', selected: false},
                    {nome: 'Carlo', selected: false}
                  ]
                },
                {
                  nome: 'Scuola',
                  ora: '08.00',
                  persone: []
                }]
            },
            {
              verso: 'ritorno',
              fermate: [{
                nome: 'Scuola',
                ora: '13.40',
                persone: []
              },
                {
                  nome: 'Via Vigone',
                  ora: '13.45',
                  persone: [
                    {nome: 'Shibo', selected: false},
                    {nome: 'Vittoria', selected: false},
                    {nome: 'Tito', selected: false},
                    {nome: 'Tiberio', selected: false},
                    {nome: 'Carlo', selected: false}
                  ]
                },
                {
                  nome: 'Via Milano',
                  ora: '13.50',
                  persone: [
                    {nome: 'Teresa', selected: false},
                    {nome: 'Francesco', selected: false},
                    {nome: 'Cristina', selected: false}
                  ]
                },
                {
                  nome: 'Piazza Castello',
                  ora: '13.55',
                  persone: [
                    {nome: 'Carlo', selected: false},
                    {nome: 'Giovanni', selected: false},
                    {nome: 'Zaira', selected: false}
                  ]
                },
                {
                  nome: 'Piazza Pitagora',
                  ora: '14.00',
                  persone: [
                    {nome: 'Maurizio', selected: false},
                    {nome: 'Mimmo', selected: false},
                    {nome: 'Laura', selected: false}
                  ]
                }]
            }
          ]
        },
        {
          data: '2019/07/20',
          tratte: [
            {
              verso: 'andata',
              fermate: [{
                nome: 'Piazza Pitagora',
                ora: '07.40',
                persone: [
                  {nome: 'Benedetta', selected: false},
                  {nome: 'Aurora', selected: false},
                  {nome: 'Chanel', selected: false},
                  {nome: 'Matteo', selected: false},
                  {nome: 'Sara', selected: false},
                  {nome: 'Simone', selected: false},
                  {nome: 'Claudia', selected: false}
                ]
              },
                {
                  nome: 'Piazza Castello',
                  ora: '07.45',
                  persone: [
                    {nome: 'Giacomo', selected: false},
                    {nome: 'Emma', selected: false}
                  ]
                },
                {
                  nome: 'Via Milano',
                  ora: '07.50',
                  persone: [
                    {nome: 'Isabel', selected: false},
                    {nome: 'Mohammed', selected: false},
                    {nome: 'Iaia', selected: false}
                  ]
                },
                {
                  nome: 'Via Vigone',
                  ora: '07.55',
                  persone: [
                    {nome: 'Shibo', selected: false},
                    {nome: 'Vittoria', selected: false}
                  ]
                },
                {
                  nome: 'Scuola',
                  ora: '08.00',
                  persone: []
                }]
            },
            {
              verso: 'ritorno',
              fermate: [{
                nome: 'Scuola',
                ora: '13.40',
                persone: []
              },
                {
                  nome: 'Via Vigone',
                  ora: '13.45',
                  persone: [
                    {nome: 'Shibo', selected: false},
                    {nome: 'Vittoria', selected: false}
                  ]
                },
                {
                  nome: 'Via Milano',
                  ora: '13.50',
                  persone: [
                    {nome: 'Isabel', selected: false},
                    {nome: 'Mohammed', selected: false},
                    {nome: 'Iaia', selected: false}
                  ]
                },
                {
                  nome: 'Piazza Castello',
                  ora: '13.55',
                  persone: [
                    {nome: 'Giacomo', selected: false},
                    {nome: 'Emma', selected: false}
                  ]
                },
                {
                  nome: 'Piazza Pitagora',
                  ora: '14.00',
                  persone: [
                    {nome: 'Benedetta', selected: false},
                    {nome: 'Aurora', selected: false},
                    {nome: 'Chanel', selected: false},
                    {nome: 'Matteo', selected: false},
                    {nome: 'Sara', selected: false},
                    {nome: 'Simone', selected: false},
                    {nome: 'Claudia', selected: false}
                  ]
                }]
            }
          ]
        }
      ]
    }
  ];

  pageEvent: PageEvent;
  length: number;
  pageSize = 1;
  pageIndex: number;
  lineaSelezionataMenu = 0;

  ngOnInit(): void {
    this.length = this.linee[this.lineaSelezionataMenu].corse.length;
    // tslint:disable-next-line:prefer-for-of
    for (let i = 0; i < this.linee.length; i++) {
      this.linee[i].corse.sort((a, b) => a.data.localeCompare(b.data));
    }

    let j;
    let date;
    const today = new Date();
    const dd = String(today.getDate()).padStart(2, '0');
    const mm = String(today.getMonth() + 1).padStart(2, '0'); // January is 0!
    const yyyy = today.getFullYear();
    const cur = yyyy + '/' + mm + '/' + dd;
    const curdate = new Date(cur).getTime();

    // tslint:disable-next-line:prefer-for-of
    for (j = 0; j < this.linee[this.lineaSelezionataMenu].corse.length; j++) {
      date = new Date(this.linee[this.lineaSelezionataMenu].corse[j].data).getTime();
      if (date - curdate >= 0) {
        break;
      }
    }
    this.pageIndex = j;
  }

  clickPersona($event: MouseEvent, verso, idFermata, idPersona) {
    const date = new Date(this.linee[this.lineaSelezionataMenu].corse[this.pageIndex].data).getTime();
    const today = new Date();
    const dd = String(today.getDate()).padStart(2, '0');
    const mm = String(today.getMonth() + 1).padStart(2, '0'); // January is 0!
    const yyyy = today.getFullYear();
    const cur = yyyy + '/' + mm + '/' + dd;
    const curdate = new Date(cur).getTime();

    // tslint:disable-next-line:max-line-length
    if (this.linee[this.lineaSelezionataMenu].corse[this.pageIndex].tratte[verso].fermate[idFermata].persone[idPersona].selected === false && (date - curdate) === 0) {
      this.linee[this.lineaSelezionataMenu].corse[this.pageIndex].tratte[verso].fermate[idFermata].persone[idPersona].selected = true;
    } else {
      this.linee[this.lineaSelezionataMenu].corse[this.pageIndex].tratte[verso].fermate[idFermata].persone[idPersona].selected = false;
    }
  }

  selezionaPersona(verso: number, idFermata: number, idPersona: number) {
    if (this.linee[this.lineaSelezionataMenu].corse[this.pageIndex].tratte[verso].fermate[idFermata].persone[idPersona].selected === true) {
      return 'personaSelezionata';
    }
  }

  personeOrdinateByNome(verso: number, idFermata: number) {
    // tslint:disable-next-line:max-line-length
    return this.linee[this.lineaSelezionataMenu].corse[this.pageIndex].tratte[verso].fermate[idFermata].persone.sort((a, b) => a.nome.localeCompare(b.nome));
  }

  fermateOrdinateByOra(verso: number) {
    return this.linee[this.lineaSelezionataMenu].corse[this.pageIndex].tratte[verso].fermate.sort((a, b) => a.ora.localeCompare(b.ora));
  }

  selezionaLineaMenu($event: MouseEvent, idLinea: number) {
    this.lineaSelezionataMenu = idLinea;
    this.length = this.linee[idLinea].corse.length;
    let j;
    let date;
    const today = new Date();
    const dd = String(today.getDate()).padStart(2, '0');
    const mm = String(today.getMonth() + 1).padStart(2, '0'); // January is 0!
    const yyyy = today.getFullYear();
    const cur = yyyy + '/' + mm + '/' + dd;
    const curdate = new Date(cur).getTime();

    // tslint:disable-next-line:prefer-for-of
    for (j = 0; j < this.linee[this.lineaSelezionataMenu].corse.length; j++) {
      date = new Date(this.linee[this.lineaSelezionataMenu].corse[j].data).getTime();
      if (date - curdate >= 0) {
        break;
      }
    }
    this.pageIndex = j;
  }

  selezionaCorsaPaginator(pageEvent: PageEvent) {
    let i;
    let date;
    const today = new Date();
    const dd = String(today.getDate()).padStart(2, '0');
    const mm = String(today.getMonth() + 1).padStart(2, '0'); // January is 0!
    const yyyy = today.getFullYear();
    const cur = yyyy + '/' + mm + '/' + dd;
    const curdate = new Date(cur).getTime();

    if (pageEvent === undefined) {
      for (i = 0; i < this.linee[this.lineaSelezionataMenu].corse.length; i++) {
        date = new Date(this.linee[this.lineaSelezionataMenu].corse[i].data).getTime();
        if (date - curdate >= 0) {
          break;
        }
      }
      this.pageIndex = i;
      return i;
    } else {
      return pageEvent.pageIndex;
    }
  }
}

