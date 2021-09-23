import { Component } from '@angular/core';
import { AppService } from './app.service'

@Component({
    selector: 'home-header',
    providers: [AppService],
    template: `<div class="container" >
    <button *ngIf="!isLoggedIn" class="btn btn-primary" (click)="login()" type="submit">Login</button>
    <div *ngIf="isLoggedIn" class="content">
        <span>Welcome !!</span>
        <a class="btn btn-default pull-right"(click)="logout()" href="#">Logout</a> <br/><br/>
        <a class="btn btn-default pull-right"(click)="refreshAccessToken()" href="#">Refresh Token</a>
        <br/>
        <foo-details></foo-details>
    </div>
</div>`
})

export class HomeComponent {
    public isLoggedIn = false;

    constructor(
        private _service: AppService) { }

    ngOnInit() {
        this.isLoggedIn = this._service.checkCredentials();
        let i = window.location.href.indexOf('redirect');
        if (!this.isLoggedIn && i != -1) {
            this._service.retrieveToken();
        }
    }

    login() {
        window.location.href = 'http://localhost:8089/auth/code';
    }

    logout() {
        this._service.logout();
    }

    refreshAccessToken() {
        this._service.refreshAccessToken();
    }

}