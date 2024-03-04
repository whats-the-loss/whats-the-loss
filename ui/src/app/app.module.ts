import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import {MatDrawer, MatDrawerContent} from "@angular/material/sidenav";
import {AppComponent} from "./app.component";



@NgModule({
  declarations: [
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    MatDrawer,
    MatDrawerContent
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
