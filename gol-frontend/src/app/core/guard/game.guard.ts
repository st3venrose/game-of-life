import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree } from '@angular/router';
import { LocalStorageService } from '../services';
import { TABLE_SIZE_STOREGE_KEY } from '../../shared/constants/constants';

@Injectable({
  providedIn: 'root',
})
export class GameGuard implements CanActivate {
  constructor(private localStorageService: LocalStorageService) {}
  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    return !!this.localStorageService.get(TABLE_SIZE_STOREGE_KEY);
  }
}
