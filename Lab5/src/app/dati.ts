import {Linea} from './linea';

export const LINE: Linea[] = [
    {
      nome: 'Santa_Rita-Politecnico',
      corse: [
        {
          data: '2019-04-30',
          tratte: [
            {
              verso: 'A',
              fermate: [{
                nome: '1',
                ora: '07.35',
                persone: [
                  {nome: 'Rebecca', selected: false},
                  {nome: 'Mario', selected: false},
                  {nome: 'Salvatore', selected: false},
                  {nome: 'Filippo', selected: false}
                ]
              },
                {
                  nome: '2',
                  ora: '07.40',
                  persone: [
                    {nome: 'Giuseppe', selected: false},
                    {nome: 'Emma', selected: false}
                  ]
                },
                {
                  nome: '3',
                  ora: '07.50',
                  persone: [
                    {nome: 'Giacomo', selected: false},
                    {nome: 'Enzo', selected: false},
                    {nome: 'Maria', selected: false}
                  ]
                },
                {
                  nome: '4',
                  ora: '07.55',
                  persone: [
                    {nome: 'Angelo', selected: false}
                  ]
                },
                {
                  nome: '5',
                  ora: '08.00',
                  persone: []
                }]
            },
            {
              verso: 'R',
              fermate: [{
                nome: '5',
                ora: '13.30',
                persone: []
              },
                {
                  nome: '4',
                  ora: '13.35',
                  persone: [
                    {nome: 'Angelo', selected: false}
                  ]
                },
                {
                  nome: '3',
                  ora: '13.40',
                  persone: [
                    {nome: 'Giacomo', selected: false},
                    {nome: 'Enzo', selected: false},
                    {nome: 'Maria', selected: false}
                  ]
                },
                {
                  nome: '2',
                  ora: '13.45',
                  persone: [
                    {nome: 'Giuseppe', selected: false},
                    {nome: 'Emma', selected: false}
                  ]
                },
                {
                  nome: '1',
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
          data: '2019-06-30',
          tratte: [
            {
              verso: 'A',
              fermate: [{
                nome: '1',
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
                  nome: '2',
                  ora: '07.40',
                  persone: [
                    {nome: 'Giacomo', selected: false},
                    {nome: 'Emma', selected: false}
                  ]
                },
                {
                  nome: '3',
                  ora: '07.50',
                  persone: [
                    {nome: 'Isabel', selected: false},
                    {nome: 'Mohammed', selected: false},
                    {nome: 'Iaia', selected: false}
                  ]
                },
                {
                  nome: '4',
                  ora: '07.55',
                  persone: [
                    {nome: 'Shibo', selected: false},
                    {nome: 'Vittoria', selected: false}
                  ]
                },
                {
                  nome: '5',
                  ora: '08.00',
                  persone: []
                }]
            },
            {
              verso: 'R',
              fermate: [{
                nome: '5',
                ora: '13.30',
                persone: []
              },
                {
                  nome: '4',
                  ora: '13.35',
                  persone: [
                    {nome: 'Shibo', selected: false},
                    {nome: 'Vittoria', selected: false}
                  ]
                },
                {
                  nome: '3',
                  ora: '13.40',
                  persone: [
                    {nome: 'Isabel', selected: false},
                    {nome: 'Mohammed', selected: false},
                    {nome: 'Iaia', selected: false}
                  ]
                },
                {
                  nome: '2',
                  ora: '13.45',
                  persone: [
                    {nome: 'Giacomo', selected: false},
                    {nome: 'Emma', selected: false}
                  ]
                },
                {
                  nome: '1',
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
      nome: 'Marmolada-Politecnico',
      corse: [
        {
          data: '2019-05-23',
          tratte: [
            {
              verso: 'A',
              fermate: [{
                nome: '13',
                ora: '07.40',
                persone: [
                  {nome: 'Maurizio', selected: false},
                  {nome: 'Mimmo', selected: false},
                  {nome: 'Laura', selected: false}
                ]
              },
                {
                  nome: '14',
                  ora: '07.40',
                  persone: [
                    {nome: 'Carlo', selected: false},
                    {nome: 'Giovanni', selected: false},
                    {nome: 'Zaira', selected: false},
                  ]
                },
                {
                  nome: '15',
                  ora: '07.45',
                  persone: [
                    {nome: 'Teresa', selected: false},
                    {nome: 'Francesco', selected: false},
                    {nome: 'Cristina', selected: false}
                  ]
                },
                {
                  nome: '16',
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
                  nome: '17',
                  ora: '08.00',
                  persone: []
                }]
            },
            {
              verso: 'R',
              fermate: [{
                nome: '17',
                ora: '13.40',
                persone: []
              },
                {
                  nome: '16',
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
                  nome: '15',
                  ora: '13.50',
                  persone: [
                    {nome: 'Teresa', selected: false},
                    {nome: 'Francesco', selected: false},
                    {nome: 'Cristina', selected: false}
                  ]
                },
                {
                  nome: '14',
                  ora: '13.55',
                  persone: [
                    {nome: 'Carlo', selected: false},
                    {nome: 'Giovanni', selected: false},
                    {nome: 'Zaira', selected: false}
                  ]
                },
                {
                  nome: '13',
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
          data: '2019-07-20',
          tratte: [
            {
              verso: 'A',
              fermate: [{
                nome: '13',
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
                  nome: '14',
                  ora: '07.45',
                  persone: [
                    {nome: 'Giacomo', selected: false},
                    {nome: 'Emma', selected: false}
                  ]
                },
                {
                  nome: '15',
                  ora: '07.50',
                  persone: [
                    {nome: 'Isabel', selected: false},
                    {nome: 'Mohammed', selected: false},
                    {nome: 'Iaia', selected: false}
                  ]
                },
                {
                  nome: '16',
                  ora: '07.55',
                  persone: [
                    {nome: 'Shibo', selected: false},
                    {nome: 'Vittoria', selected: false}
                  ]
                },
                {
                  nome: '17',
                  ora: '08.00',
                  persone: []
                }]
            },
            {
              verso: 'R',
              fermate: [{
                nome: '17',
                ora: '13.40',
                persone: []
              },
                {
                  nome: '16',
                  ora: '13.45',
                  persone: [
                    {nome: 'Shibo', selected: false},
                    {nome: 'Vittoria', selected: false}
                  ]
                },
                {
                  nome: '15',
                  ora: '13.50',
                  persone: [
                    {nome: 'Isabel', selected: false},
                    {nome: 'Mohammed', selected: false},
                    {nome: 'Iaia', selected: false}
                  ]
                },
                {
                  nome: '14',
                  ora: '13.55',
                  persone: [
                    {nome: 'Giacomo', selected: false},
                    {nome: 'Emma', selected: false}
                  ]
                },
                {
                  nome: '13',
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
    },
    {
      nome: 'Porta_Nuova-Politecnico',
      corse: [
        {
          data: '2019-05-27',
          tratte: [
            {
              verso: 'A',
              fermate: [{
                nome: '8',
                ora: '08.05',
                persone: [
                  {nome: 'Antonino', selected: false},
                  {nome: 'Paolo', selected: false},
                  {nome: 'Umberto', selected: false}
                ]
              },
                {
                  nome: '9',
                  ora: '08.10',
                  persone: [
                    {nome: 'Christian', selected: false},
                    {nome: 'Luca', selected: false},
                    {nome: 'Andrea', selected: false},
                    {nome: 'Lucia', selected: false},
                    {nome: 'Valentina', selected: false},
                  ]
                },
                {
                  nome: '10',
                  ora: '08.15',
                  persone: [
                    {nome: 'Francesco', selected: false},
                    {nome: 'Salvatore', selected: false},
                    {nome: 'Maria', selected: false},
                    {nome: 'Marianna', selected: false}
                  ]
                },
                {
                  nome: '11',
                  ora: '08.20',
                  persone: [
                    {nome: 'Kevin', selected: false},
                    {nome: 'Davide', selected: false},
                    {nome: 'Matteo', selected: false},
                    {nome: 'Gino', selected: false},
                    {nome: 'Carla', selected: false}
                  ]
                },
                {
                  nome: '12',
                  ora: '08.30',
                  persone: []
                }]
            },
            {
              verso: 'R',
              fermate: [{
                nome: '12',
                ora: '13.35',
                persone: []
              },
                {
                  nome: '11',
                  ora: '13.40',
                  persone: [
                    {nome: 'Filippo', selected: false},
                    {nome: 'Tommaso', selected: false},
                    {nome: 'Lorenzo', selected: false},
                    {nome: 'Tina', selected: false},
                    {nome: 'Rebecca', selected: false}
                  ]
                },
                {
                  nome: '10',
                  ora: '13.45',
                  persone: [
                    {nome: 'Dario', selected: false},
                    {nome: 'Emanuele', selected: false},
                    {nome: 'Valentina', selected: false}
                  ]
                },
                {
                  nome: '9',
                  ora: '13.50',
                  persone: [
                    {nome: 'Diana', selected: false},
                    {nome: 'Sonia', selected: false},
                    {nome: 'Veronica', selected: false}
                  ]
                },
                {
                  nome: '8',
                  ora: '13.55',
                  persone: [
                    {nome: 'Maura', selected: false},
                    {nome: 'Federico', selected: false},
                    {nome: 'Giorgio', selected: false}
                  ]
                }]
            }
          ]
        },
        {
          data: '2019-09-29',
          tratte: [
            {
              verso: 'A',
              fermate: [{
                nome: '8',
                ora: '08.05',
                persone: [
                  {nome: 'Silvia', selected: false},
                  {nome: 'Paolo', selected: false},
                  {nome: 'Ilaria', selected: false}
                ]
              },
                {
                  nome: '9',
                  ora: '08.10',
                  persone: [
                    {nome: 'Alessandro', selected: false},
                    {nome: 'Luca', selected: false},
                    {nome: 'Alessio', selected: false},
                    {nome: 'Laura', selected: false},
                    {nome: 'Valentina', selected: false},
                  ]
                },
                {
                  nome: '10',
                  ora: '08.15',
                  persone: [
                    {nome: 'Francesca', selected: false},
                    {nome: 'Davide', selected: false},
                    {nome: 'Daniele', selected: false},
                    {nome: 'Marianna', selected: false}
                  ]
                },
                {
                  nome: '11',
                  ora: '08.20',
                  persone: [
                    {nome: 'Kevin', selected: false},
                    {nome: 'Davide', selected: false},
                    {nome: 'Mattia', selected: false},
                    {nome: 'Ginevra', selected: false},
                    {nome: 'Gennaro', selected: false}
                  ]
                },
                {
                  nome: '12',
                  ora: '08.30',
                  persone: []
                }]
            },
            {
              verso: 'R',
              fermate: [{
                nome: '12',
                ora: '13.35',
                persone: []
              },
                {
                  nome: '11',
                  ora: '13.40',
                  persone: [
                    {nome: 'Filippo', selected: false},
                    {nome: 'Giorgio', selected: false},
                    {nome: 'Lorenza', selected: false},
                    {nome: 'Angelica', selected: false},
                  ]
                },
                {
                  nome: '10',
                  ora: '13.45',
                  persone: [
                    {nome: 'Martina', selected: false},
                    {nome: 'Lidia', selected: false},
                    {nome: 'Valentina', selected: false}
                  ]
                },
                {
                  nome: '9',
                  ora: '13.50',
                  persone: [
                    {nome: 'Debora', selected: false},
                    {nome: 'Sara', selected: false},
                    {nome: 'Camilla', selected: false}
                  ]
                },
                {
                  nome: '8',
                  ora: '13.55',
                  persone: [
                    {nome: 'Alessio', selected: false},
                    {nome: 'Francesco', selected: false},
                    {nome: 'Giovanni', selected: false}
                  ]
                }]
            }
          ]
        },
        {
          data: '2019-11-21',
          tratte: [
            {
              verso: 'A',
              fermate: [{
                nome: '8',
                ora: '08.05',
                persone: [
                  {nome: 'Stefano', selected: false},
                  {nome: 'Luigi', selected: false},
                  {nome: 'Umberto', selected: false},
                  {nome: 'Luciano', selected: false}
                ]
              },
                {
                  nome: '9',
                  ora: '08.10',
                  persone: [
                    {nome: 'Leonardo', selected: false},
                    {nome: 'Laura', selected: false},
                    {nome: 'Alberto', selected: false},
                    {nome: 'Lara', selected: false},
                    {nome: 'Valerio', selected: false},
                  ]
                },
                {
                  nome: '10',
                  ora: '08.15',
                  persone: [
                    {nome: 'Manuela', selected: false},
                    {nome: 'Marco', selected: false},
                    {nome: 'Sabrina', selected: false},
                    {nome: 'Jacopo', selected: false}
                  ]
                },
                {
                  nome: '11',
                  ora: '08.20',
                  persone: [
                    {nome: 'Kevin', selected: false},
                    {nome: 'Daniel', selected: false},
                    {nome: 'Ilaria', selected: false},
                    {nome: 'Cecilia', selected: false},
                    {nome: 'Carla', selected: false}
                  ]
                },
                {
                  nome: '12',
                  ora: '08.30',
                  persone: []
                }]
            },
            {
              verso: 'R',
              fermate: [{
                nome: '12',
                ora: '13.35',
                persone: []
              },
                {
                  nome: '11',
                  ora: '13.40',
                  persone: [
                    {nome: 'Angelo', selected: false},
                    {nome: 'Marina', selected: false},
                    {nome: 'Valentino', selected: false},
                    {nome: 'Antonio', selected: false}
                  ]
                },
                {
                  nome: '10',
                  ora: '13.45',
                  persone: [
                    {nome: 'Davide', selected: false},
                    {nome: 'Elena', selected: false},
                    {nome: 'Vittoria', selected: false}
                  ]
                },
                {
                  nome: '9',
                  ora: '13.50',
                  persone: [
                    {nome: 'Diana', selected: false},
                    {nome: 'Sonia', selected: false},
                    {nome: 'Veronica', selected: false},
                    {nome: 'Andrea', selected: false},
                    {nome: 'Matteo', selected: false},
                    {nome: 'Riccardo', selected: false}
                  ]
                },
                {
                  nome: '8',
                  ora: '13.55',
                  persone: [
                    {nome: 'Anna', selected: false},
                    {nome: 'Nicola', selected: false},
                    {nome: 'Gino', selected: false}
                  ]
                }]
            }
          ]
        }
      ]
    }
];
