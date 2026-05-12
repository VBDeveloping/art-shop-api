import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { FrameResponse } from '../models/art-order.model';

@Injectable({ providedIn: 'root' })
export class FrameService {
  private readonly API_URL = 'http://localhost:8080/api/frames';

  constructor(private http: HttpClient) {}

  findAll(): Observable<FrameResponse[]> {
    return this.http.get<FrameResponse[]>(this.API_URL);
  }
}
