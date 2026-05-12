import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { GlassResponse } from '../models/art-order.model';

@Injectable({ providedIn: 'root' })
export class GlassService {
  private readonly API_URL = 'http://localhost:8080/api/glasses';

  constructor(private http: HttpClient) {}

  findAll(): Observable<GlassResponse[]> {
    return this.http.get<GlassResponse[]>(this.API_URL);
  }
}
