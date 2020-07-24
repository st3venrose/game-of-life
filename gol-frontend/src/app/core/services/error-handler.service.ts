import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ErrorHandlerService {
  private hasError$: BehaviorSubject<boolean> = new BehaviorSubject(false);

  getErrorModel(): BehaviorSubject<boolean> {
    return this.hasError$;
  }

  handleError() {
    this.hasError$.next(true);
  }

  removeError() {
    this.hasError$.next(false);
  }
}
