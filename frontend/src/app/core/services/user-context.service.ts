import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { UserResponse } from '../models/art-order.model';
import { AuthService } from './auth.service';

@Injectable({ providedIn: 'root' })
export class UserContextService {

  private readonly API_URL = 'http://localhost:8080/api/users';
  private currentUserSubject = new BehaviorSubject<UserResponse | null>(null);
  currentUser$ = this.currentUserSubject.asObservable();

  constructor(private http: HttpClient, private authService: AuthService) {}

  loadCurrentUser(): Observable<UserResponse[]> {
    return this.http.get<UserResponse[]>(this.API_URL).pipe(
      tap(users => {
        const email = this.authService.getUserEmail();
        const user = users.find(u => u.email === email) ?? null;
        this.currentUserSubject.next(user);
      })
    );
  }

  getCurrentUser(): UserResponse | null {
    return this.currentUserSubject.getValue();
  }

  hasRole(...roles: string[]): boolean {
    const user = this.getCurrentUser();
    if (!user) return false;
    return roles.includes(user.role);
  }

  isAdmin(): boolean {
    return this.hasRole('ADMIN');
  }

  isSeller(): boolean {
    return this.hasRole('ADMIN', 'SELLER');
  }

  isStock(): boolean {
    return this.hasRole('ADMIN', 'STOCK');
  }

  isFinancial(): boolean {
    return this.hasRole('ADMIN', 'FINANCIAL');
  }

  clear(): void {
    this.currentUserSubject.next(null);
  }
}
