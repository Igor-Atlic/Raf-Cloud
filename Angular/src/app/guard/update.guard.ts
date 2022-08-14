import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import {UserService} from "../user.service"


@Injectable({
  providedIn: 'root'
})
export class UpdateGuard implements CanActivate {

  constructor(private userService: UserService) {}
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    if(this.userService.getPermissions().includes(this.userService.getUpdate())){
          return true;
        }else{
          return false;
        }
  }

}
