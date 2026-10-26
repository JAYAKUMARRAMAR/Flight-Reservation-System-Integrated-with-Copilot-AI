import { TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { App } from './app';

describe('App', () => {
  let httpTesting: HttpTestingController;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [App],
      providers: [provideHttpClient(), provideHttpClientTesting()],
    }).compileComponents();
    httpTesting = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpTesting.verify();
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(App);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it('should render title', async () => {
    const fixture = TestBed.createComponent(App);
    await fixture.whenStable();
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('h1')?.textContent).toContain('Flight CheckIn');
    expect(compiled.querySelector('#chat-heading')?.textContent).toContain('AI Assistant');
  });

  it('toggles the side chat panel from the AI Assistant button', () => {
    const fixture = TestBed.createComponent(App);
    fixture.detectChanges();
    const compiled = fixture.nativeElement as HTMLElement;
    const toggle = compiled.querySelector('.ai-assistant-toggle') as HTMLButtonElement;
    const panel = compiled.querySelector('#ai-chat-panel') as HTMLElement;

    expect(toggle.getAttribute('aria-expanded')).toBe('false');
    expect(panel.getAttribute('aria-hidden')).toBe('true');

    toggle.click();
    fixture.detectChanges();
    expect(toggle.getAttribute('aria-expanded')).toBe('true');
    expect(panel.getAttribute('aria-hidden')).toBe('false');

    toggle.click();
    fixture.detectChanges();
    expect(toggle.getAttribute('aria-expanded')).toBe('false');
    expect(panel.getAttribute('aria-hidden')).toBe('true');
  });

  it('sends a message and displays the reply', () => {
    const fixture = TestBed.createComponent(App);
    fixture.detectChanges();
    const compiled = fixture.nativeElement as HTMLElement;
    compiled.querySelector('.ai-assistant-toggle')?.dispatchEvent(new Event('click'));
    fixture.detectChanges();
    const input = compiled.querySelector('input') as HTMLInputElement;
    input.value = '  Where can I check in?  ';
    input.dispatchEvent(new Event('input'));
    fixture.detectChanges();
    compiled.querySelector('form')?.dispatchEvent(new Event('submit'));
    fixture.detectChanges();

    const request = httpTesting.expectOne('http://localhost:8083/flightreservation/api/chat');
    expect(request.request.method).toBe('POST');
    expect(request.request.body).toEqual({ message: 'Where can I check in?' });
    expect(compiled.textContent).toContain('Assistant is thinking...');

    request.flush({ reply: 'You can check in online.' });
    fixture.detectChanges();

    expect(compiled.textContent).toContain('Where can I check in?');
    expect(compiled.textContent).toContain('You can check in online.');
  });

  it('displays an error when the chat request fails', () => {
    const fixture = TestBed.createComponent(App);
    fixture.detectChanges();
    const compiled = fixture.nativeElement as HTMLElement;
    compiled.querySelector('.ai-assistant-toggle')?.dispatchEvent(new Event('click'));
    fixture.detectChanges();
    const input = compiled.querySelector('input') as HTMLInputElement;
    input.value = 'Help with my flight';
    input.dispatchEvent(new Event('input'));
    fixture.detectChanges();
    compiled.querySelector('form')?.dispatchEvent(new Event('submit'));

    httpTesting.expectOne('http://localhost:8083/flightreservation/api/chat').flush(
      { error: 'AI provider unavailable' },
      { status: 503, statusText: 'Service Unavailable' }
    );
    fixture.detectChanges();

    expect(compiled.querySelector('[role="alert"]')?.textContent).toContain('could not be sent');
  });
});
