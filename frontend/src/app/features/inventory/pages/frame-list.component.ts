import { Component, OnInit } from '@angular/core';
import { CommonModule, CurrencyPipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { FrameService } from '../../../core/services/frame.service';
import { UserContextService } from '../../../core/services/user-context.service';
import { FrameResponse } from '../../../core/models/art-order.model';
import { AppHeaderComponent } from '../../../shared/components/organisms/app-header/app-header.component';


@Component({
  selector: 'app-frame-list',
  standalone: true,
  imports: [CommonModule, CurrencyPipe, FormsModule, AppHeaderComponent],
  templateUrl: './frame-list.component.html',
  styleUrl: './frame-list.component.scss'
})
export class FrameListComponent implements OnInit {

  frames: FrameResponse[] = [];
  loading = true;
  errorMessage = '';
  showForm = false;

  newFrame = {
    code: '',
    name: '',
    material: '',
    color: '',
    faceWidthCm: null as number | null,
    cuttingMarginCm: null as number | null,
    pricePerMeter: null as number | null,
    stockMeters: null as number | null
  };

  constructor(
    private frameService: FrameService,
    public userContext: UserContextService,
    private router: Router
  ) {}

  ngOnInit(): void { this.load(); }

  load(): void {
    this.frameService.findAll().subscribe({
      next: (data) => { this.frames = data; this.loading = false; },
      error: () => { this.errorMessage = 'Erro ao carregar molduras.'; this.loading = false; }
    });
  }

  save(): void {
    this.frameService.create(this.newFrame as any).subscribe({
      next: () => {
        this.showForm = false;
        this.newFrame = { code: '', name: '', material: '', color: '', faceWidthCm: null, cuttingMarginCm: null, pricePerMeter: null, stockMeters: null };
        this.load();
      },
      error: () => this.errorMessage = 'Erro ao salvar moldura.'
    });
  }

  addStock(id: number, meters: number): void {
    this.frameService.addStock(id, meters).subscribe({ next: () => this.load() });
  }

  back(): void { this.router.navigate(['/dashboard']); }
}
