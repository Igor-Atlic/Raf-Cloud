import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './components/app/app.component';
import { HomeComponent } from './components/home/home.component';
import { UsersComponent } from './components/users/users.component';
import { AdduserComponent } from './components/adduser/adduser.component';
import { EdituserComponent } from './components/edituser/edituser.component';
import { MachineComponent } from './components/machine/machine.component';
import { AddmachineComponent } from './components/addmachine/addmachine.component';
import { ScheduleComponent } from './components/schedule/schedule.component';
import { ErrorComponent } from './components/error/error.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    UsersComponent,
    AdduserComponent,
    EdituserComponent,
    MachineComponent,
    AddmachineComponent,
    ScheduleComponent,
    ErrorComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
