import { Injectable } from '@angular/core';
import { Cookie } from 'ng2-cookies';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import { Router } from '@angular/router';

export class Foo {
  constructor(
    public id: number,
    public name: string) { }
}

@Injectable()
export class AppService {

  constructor(
    private _http: HttpClient, private router: Router) { }

  retrieveToken() {
    let headers = new HttpHeaders({
      'Content-type': 'application/x-www-form-urlencoded; charset=utf-8'
    });
    this._http.post('auth/token', {}, { headers: headers })
      .subscribe(
        data => this.saveToken(data),
        err => alert('Invalid Credentials')
      );
  }

  saveToken(token) {
    var expireDate = new Date().getTime() + (1000 * token.expires_in);
    Cookie.set("access_token", token.access_token, expireDate);
    console.log('Obtained Access token');
    this.router.navigate(['/']);
  }

  getResource(resourceUrl): Observable<any> {
    var headers = new HttpHeaders({
      'Content-type': 'application/x-www-form-urlencoded; charset=utf-8',
      'Authorization': 'Bearer ' + Cookie.get('access_token')
    });
    return this._http.get(resourceUrl, { headers: headers })
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }

  checkCredentials() {
    return (Cookie.check('access_token'));
  }

  logout() {
    let headers = new HttpHeaders({
      'Content-type': 'application/x-www-form-urlencoded; charset=utf-8'
    });
    
    this._http.post('auth/refresh/revoke', {}, { headers: headers })
      .subscribe(
        data => {
        	Cookie.delete('access_token');
        	window.location.href = 'http://localhost:8089/';
        	},
        err => alert('Could not logout')
      );
  }

  refreshAccessToken() {
    let headers = new HttpHeaders({
      'Content-type': 'application/x-www-form-urlencoded; charset=utf-8'
    });
    this._http.post('auth/refresh', {}, {headers: headers })
      .subscribe(
        data => this.saveToken(data),
        err => alert('Invalid Credentials')
      );
  }
}