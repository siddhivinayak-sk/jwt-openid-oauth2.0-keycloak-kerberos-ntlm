import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';

import { AppComponent } from './app.component';
import { HomeComponent } from './home.component';
import { FooComponent } from './foo.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    FooComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    RouterModule.forRoot([
      { path: '', component: HomeComponent, pathMatch: 'full' },
      { path: '**', component: HomeComponent }], { onSameUrlNavigation: 'reload' })
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
