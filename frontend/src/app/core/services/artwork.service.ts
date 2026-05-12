import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ArtworkResponse } from '../models/art-order.model';

@Injectable({ providedIn: 'root' })
export class ArtworkService {
  private readonly API_URL = 'http://localhost:8080/api/art-works';

  constructor(private http: HttpClient) {}

  findAll(): Observable<ArtworkResponse[]> {
    return this.http.get<ArtworkResponse[]>(this.API_URL);
  }
}
