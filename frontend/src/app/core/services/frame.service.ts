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

  create(frame: any): Observable<FrameResponse> {
    return this.http.post<FrameResponse>(this.API_URL, frame);
  }

  addStock(id: number, meters: number): Observable<FrameResponse> {
    return this.http.patch<FrameResponse>(`${this.API_URL}/${id}/add-stock?meters=${meters}`, {});
  }
}
