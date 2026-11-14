import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserRequest, UserResponse } from '../models/user.model';

@Injectable({ providedIn: 'root' })
export class UserManagementService {

  private readonly API_URL = 'http://localhost:8080/api/users';

  constructor(private http: HttpClient) {}

  findAll(): Observable<UserResponse[]> {
    return this.http.get<UserResponse[]>(this.API_URL);
  }

  create(user: UserRequest): Observable<UserResponse> {
    return this.http.post<UserResponse>(this.API_URL, user);
  }

  toggleStatus(id: number): Observable<void> {
    return this.http.patch<void>(`${this.API_URL}/${id}/toggle-status`, {});
  }
}
