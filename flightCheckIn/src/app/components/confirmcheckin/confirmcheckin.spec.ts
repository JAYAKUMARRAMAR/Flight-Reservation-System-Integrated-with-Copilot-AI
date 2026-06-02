import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Confirmcheckin } from './confirmcheckin';

describe('Confirmcheckin', () => {
  let component: Confirmcheckin;
  let fixture: ComponentFixture<Confirmcheckin>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Confirmcheckin],
    }).compileComponents();

    fixture = TestBed.createComponent(Confirmcheckin);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
