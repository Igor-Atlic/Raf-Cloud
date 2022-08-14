import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot, UrlTree } from '@angular/router';
import {UserService} from "../user.service"
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ReadGuard implements CanActivate {

  constructor(private userService: UserService) {}
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    if(this.userService.getPermissions().includes(this.userService.getRead())){
      return true;
    }else{
      return false;
    }


  }

}
