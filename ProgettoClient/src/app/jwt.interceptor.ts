import {Injectable} from '@angular/core';
import {HttpRequest, HttpHandler, HttpEvent, HttpInterceptor, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {tap} from 'rxjs/operators';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // add authorization header with jwt token if available
    let currentUser = localStorage.getItem('access_token');
    if (currentUser) {
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${currentUser}`
        }
      });
    }

    return next.handle(request);

    /* .pipe(tap((event: HttpEvent<any>) => {
        if(event instanceof HttpResponse){
          // if the token is valid
        }
      }, (err: any) => {
        // if the token has expired.
        // this is where you can do anything like navigating
        this.authenticationService.logout();
        this.router.navigateByUrl('/login');
        this.alertService.error("JWT SCADUTO", true);
      })) */
  }
}
