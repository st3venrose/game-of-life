import {
  Component,
  OnInit,
  Input,
  Output,
  EventEmitter,
  ElementRef,
} from '@angular/core';
import { Observable, Subject, BehaviorSubject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

enum TOGGLE_STATUS {
  OPEN,
  CLOSE,
}

@Component({
  selector: 'gol-size-selector',
  templateUrl: './size-selector.component.html',
  styleUrls: ['./size-selector.component.scss'],
})
export class SizeSelectorComponent implements OnInit {
  TOGGLE_STATUS = TOGGLE_STATUS;

  @Input() options: number[] = [];

  @Input() defaultSelectedValue: number;

  @Output() valueChanged = new EventEmitter<number>();

  selectedOption: number;

  private onDocumentClickBound;

  private ngUnsubscribe: Subject<void> = new Subject<void>(); //TODO

  private status$: BehaviorSubject<TOGGLE_STATUS> = new BehaviorSubject(
    TOGGLE_STATUS.CLOSE
  );

  constructor(private elementRef: ElementRef) {}

  ngOnInit(): void {
    this.selectedOption = this.defaultSelectedValue || this.options[0];
    this.closeSelectOnDocumentClick();
  }

  toggleOptionsVisibility() {
    if (this.isOpened) {
      this.close();
    } else {
      this.open();
    }
  }

  selectOption(option) {
    this.selectedOption = option;
    this.onSelecteValueChanged(this.selectedOption);
  }

  open() {
    this.status$.next(TOGGLE_STATUS.OPEN);
  }

  close() {
    this.status$.next(TOGGLE_STATUS.CLOSE);
  }

  statusChange(): Observable<TOGGLE_STATUS> {
    return this.status$.asObservable();
  }

  closeSelectOnDocumentClick() {
    this.statusChange()
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe((newStatus: TOGGLE_STATUS) => {
        if (newStatus === TOGGLE_STATUS.OPEN) {
          // Listen to click events to realise when to close the dropdown.
          if (!this.onDocumentClickBound) {
            this.onDocumentClickBound = this.onDocumentClick.bind(this);
          }
          document.addEventListener('click', this.onDocumentClickBound, true);
        } else {
          document.removeEventListener(
            'click',
            this.onDocumentClickBound,
            true
          );
        }
      });
  }

  onDocumentClick(event: MouseEvent) {
    const target: EventTarget = event.target;
    if (
      target instanceof HTMLElement &&
      target.hasAttribute('dropdownToggle')
    ) {
      // Ignore dropdownToggle element, even if it's outside the menu.
      return;
    }
    const isInsideClick = this.elementRef.nativeElement.contains(target);
    if (!isInsideClick) {
      this.close();
    } else {
      this.open();
    }
  }

  onSelecteValueChanged(selectedOption: number): void {
    this.valueChanged.emit(selectedOption);
  }

  get isOpened() {
    return this.status$.getValue() === TOGGLE_STATUS.OPEN;
  }
}
