import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule, RouterOutlet } from '@angular/router';
import { Chat } from './services/chat';

@Component({
  selector: 'app-root',
  imports: [FormsModule, RouterOutlet, RouterModule],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected message = '';
  protected messages: { role: 'user' | 'assistant'; text: string }[] = [];
  protected isLoading = false;
  protected errorMessage = '';
  protected isChatOpen = false;

  constructor(private readonly chat: Chat) {}

  protected toggleChat(): void {
    this.isChatOpen = !this.isChatOpen;
  }

  protected sendMessage(): void {
    const message = this.message.trim();
    if (!message || this.isLoading) {
      return;
    }

    this.messages.push({ role: 'user', text: message });
    this.message = '';
    this.errorMessage = '';
    this.isLoading = true;

    this.chat.sendMessage(message).subscribe({
      next: ({ reply }) => {
        this.messages.push({ role: 'assistant', text: reply });
        this.isLoading = false;
      },
      error: () => {
        this.errorMessage = 'Sorry, your message could not be sent. Please try again.';
        this.isLoading = false;
      }
    });
  }
}
