import { Component, OnInit } from '@angular/core';
import { CommonModule, CurrencyPipe } from '@angular/common';
import { Router } from '@angular/router';
import { FrameService } from '../../../core/services/frame.service';
import { FrameResponse } from '../../../core/models/art-order.model';

@Component({
  selector: 'app-frame-list',
  standalone: true,
  imports: [CommonModule, CurrencyPipe],
  templateUrl: './frame-list.component.html',
  styleUrl: './frame-list.component.scss'
})
export class FrameListComponent implements OnInit {

  frames: FrameResponse[] = [];
  loading = true;
  errorMessage = '';

  constructor(
    private frameService: FrameService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.frameService.findAll().subscribe({
      next: (data) => {
        this.frames = data;
        this.loading = false;
      },
      error: () => {
        this.errorMessage = 'Erro ao carregar molduras.';
        this.loading = false;
      }
    });
  }

  back(): void {
    this.router.navigate(['/orders']);
  }
}
