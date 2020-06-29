import { Injectable, Inject } from '@angular/core';
import { DOCUMENT } from '@angular/common';

@Injectable({
  providedIn: 'root',
})
export class LocalStorageService {
  private localStorage: Storage;

  constructor(@Inject(DOCUMENT) document: any) {
    this.localStorage = document.defaultView.localStorage;
  }

  store(key: string, value: any): void {
    this.localStorage.setItem(key, JSON.stringify(value));
  }

  get(key: string): any {
    return JSON.parse(this.localStorage.getItem(key));
  }

  remove(key: string): void {
    this.localStorage.removeItem(key);
  }
}
