import { Component, OnInit } from '@angular/core';
import { CommonModule, CurrencyPipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { GlassService } from '../../../core/services/glass.service';
import { UserContextService } from '../../../core/services/user-context.service';
import { GlassResponse } from '../../../core/models/art-order.model';
import { AppHeaderComponent } from '../../../shared/components/organisms/app-header/app-header.component';


@Component({
  selector: 'app-glass-list',
  standalone: true,
  imports: [CommonModule, CurrencyPipe, FormsModule, AppHeaderComponent],
  templateUrl: './glass-list.component.html',
  styleUrl: './glass-list.component.scss'
})
export class GlassListComponent implements OnInit {

  glasses: GlassResponse[] = [];
  loading = true;
  errorMessage = '';
  showForm = false;

  newGlass = {
    type: '',
    description: '',
    pricePerSquareMeter: null as number | null,
    technicalGapCm: null as number | null
  };

  constructor(
    private glassService: GlassService,
    public userContext: UserContextService,
    private router: Router
  ) {}

  ngOnInit(): void { this.load(); }

  load(): void {
    this.glassService.findAll().subscribe({
      next: (data) => { this.glasses = data; this.loading = false; },
      error: () => { this.errorMessage = 'Erro ao carregar vidros.'; this.loading = false; }
    });
  }

  save(): void {
    this.glassService.create(this.newGlass as any).subscribe({
      next: () => {
        this.showForm = false;
        this.newGlass = { type: '', description: '', pricePerSquareMeter: null, technicalGapCm: null };
        this.load();
      },
      error: () => this.errorMessage = 'Erro ao salvar vidro.'
    });
  }

  back(): void { this.router.navigate(['/dashboard']); }
}
