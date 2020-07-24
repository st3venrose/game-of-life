import { Component } from '@angular/core';
import { ErrorHandlerService } from '../../../core/services';

@Component({
  selector: 'gol-error',
  templateUrl: './error.component.html',
  styleUrls: ['./error.component.scss'],
})
export class ErrorComponent {
  showError: Boolean;

  constructor(private errorHandlerService: ErrorHandlerService) {
    this.errorHandlerService.getErrorModel().subscribe((state: boolean) => {
      this.showError = state;
    });
  }

  hide() {
    this.errorHandlerService.removeError();
  }
}
