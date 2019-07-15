import {Injectable} from '@angular/core';

import * as SockJs from 'sockjs-client';
import * as Stomp from 'stompjs';

@Injectable()
export class WebSocketService {

  // Open connection with the back-end socket
  public connect() {
    const socket = new SockJs(`http://localhost:8080/socket`);

    const stompClient = Stomp.over(socket);

    return stompClient;
  }
}
