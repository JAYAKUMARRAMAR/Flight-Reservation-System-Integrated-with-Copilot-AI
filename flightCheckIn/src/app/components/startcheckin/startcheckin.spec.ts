import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Startcheckin } from './startcheckin';

describe('Startcheckin', () => {
  let component: Startcheckin;
  let fixture: ComponentFixture<Startcheckin>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Startcheckin],
    }).compileComponents();

    fixture = TestBed.createComponent(Startcheckin);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
