import { Component,OnInit } from '@angular/core';
import {UserService} from "../../user.service"

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'domaci3f';
  constructor(public userService:UserService) {}

  ngOnInit(): void {
      if(localStorage.getItem('token') === null){
            localStorage.setItem('token', '');
          }
            this.userService.setToken(localStorage.getItem('token')!);
      if(localStorage.getItem('permissions') === null){
                localStorage.setItem('permissions', '');
              }
                this.userService.setPermissions(JSON.parse(localStorage.getItem('permissions')!));
    }
}
