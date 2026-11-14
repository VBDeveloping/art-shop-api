import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { UserContextService } from '../../../../core/services/user-context.service';
import { UserResponse } from '../../../../core/models/art-order.model';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './app-header.component.html',
  styleUrl: './app-header.component.scss'
})
export class AppHeaderComponent implements OnInit {

  @Input() showNav = true;
  @Input() isAttendance = false;
  @Input() backRoute: string | null = null;
  @Input() backLabel = '← Voltar';

  showDropdown = false;
  currentUser: UserResponse | null = null;

  constructor(
    public userContext: UserContextService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.userContext.currentUser$.subscribe(user => {
      this.currentUser = user;
    });
  }

  isStock(): boolean {
    return !!this.currentUser && ['ADMIN', 'STOCK'].includes(this.currentUser.role);
  }

  isFinancial(): boolean {
    return !!this.currentUser && ['ADMIN', 'FINANCIAL'].includes(this.currentUser.role);
  }

  isAdmin(): boolean {
    return !!this.currentUser && this.currentUser.role === 'ADMIN';
  }

  goHome(): void {
    if (this.isAttendance) {
      const confirmed = confirm('Deseja cancelar o atendimento? Os dados serão perdidos.');
      if (!confirmed) return;
    }
    this.router.navigate(['/dashboard']);
  }

  goBack(): void {
    if (this.backRoute) this.router.navigate([this.backRoute]);
  }

  navigate(path: string): void {
    this.showDropdown = false;
    this.router.navigate([path]);
  }

  logout(): void {
    this.userContext.clear();
    this.router.navigate(['/login']);
  }

  toggleDropdown(): void {
    this.showDropdown = !this.showDropdown;
  }
}
