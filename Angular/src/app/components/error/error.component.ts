import { Component, OnInit } from '@angular/core';
import { HttpService } from 'src/app/http.service';
import { UserService } from 'src/app/user.service';
import { Errors } from 'src/app/model';

@Component({
  selector: 'app-error',
  templateUrl: './error.component.html',
  styleUrls: ['./error.component.css']
})
export class ErrorComponent implements OnInit {


  errors: Errors[] = []
  error:boolean;
  errorMessage: string;
  constructor(private httpService: HttpService, public userService:UserService) {
    this.error = false;
    this.errorMessage = "";

   }

  ngOnInit(): void {

    this.httpService.getError("http://localhost:8080/api/machine/error",this.userService.getToken()).subscribe((e)=>{
       this.errors = e;
      })
  }

}
