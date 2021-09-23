import {Component} from '@angular/core';
import {AppService} from './app.service'
 
@Component({
    selector: 'home-header',
    providers: [AppService],
  template: `<div class="container" >
    <button *ngIf="!isLoggedIn" class="btn btn-primary" (click)="login()" type="submit">Login</button>
    <div *ngIf="isLoggedIn" class="content">
        <span>Welcome !!</span>
        <a class="btn btn-default pull-right"(click)="logout()" href="#">Logout</a>
        <br/>
        <foo-details></foo-details>
    </div>
</div>`
})
 
export class HomeComponent {
     public isLoggedIn = false;

    constructor(
        private _service:AppService){}
 
    ngOnInit(){
        this.isLoggedIn = this._service.checkCredentials();    
        let i = window.location.href.indexOf('code');
        if(!this.isLoggedIn && i != -1){
            this._service.retrieveToken(window.location.href.substring(i + 5));
        }
    }

    login() {
        window.location.href = 'http://localhost:8083/auth/realms/skent/protocol/openid-connect/auth?response_type=code&scope=openid%20write%20read&client_id=' + 
          this._service.clientId + '&redirect_uri='+ this._service.redirectUri;
    }
 
    logout() {
        this._service.logout();
    }
}