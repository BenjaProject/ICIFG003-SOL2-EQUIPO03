import { Component, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ContactoService } from '../../services/contacto.service';
import { HttpErrorResponse } from '@angular/common/http'; 

@Component({
  selector: 'app-contacto',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './contacto.html',
  styleUrl: './contacto.css',
})
export class Contacto {
  private readonly contactoService = inject(ContactoService);

  readonly nombre = signal<string>('');
  readonly correo = signal<string>('');
  readonly mensaje = signal<string>('');
  
  readonly errors = signal<{ nombre?: string; correo?: string; mensaje?: string }>({});
  readonly submitted = signal<boolean>(false);
  readonly sending = signal<boolean>(false);
  readonly successMessage = signal<string | null>(null);
  readonly errorMessage = signal<string | null>(null);

  // REQ12: (parte 1/¿?) Función para validar dinámicamente el campo mientras el usuario escribe
  onInputChange(campo: 'nombre' | 'correo' | 'mensaje', valor: string): void {
    if (campo === 'nombre') this.nombre.set(valor);
    if (campo === 'correo') this.correo.set(valor);
    if (campo === 'mensaje') this.mensaje.set(valor);

    if (this.submitted()) {
      const currentErrors = { ...this.errors() };
      
      if (campo === 'nombre' && valor.trim()) delete currentErrors.nombre;
      
      if (campo === 'correo') {
        const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
        if (valor.trim() && emailRegex.test(valor.trim())) delete currentErrors.correo;
      }
      
      if (campo === 'mensaje' && valor.trim().length >= 20) delete currentErrors.mensaje;

      this.errors.set(currentErrors);
    }
  }

  onSubmit(event: Event): void {
    event.preventDefault();
    this.submitted.set(true);
    this.successMessage.set(null);
    this.errorMessage.set(null);

    const validationErrors: { nombre?: string; correo?: string; mensaje?: string } = {};

    // REQ12/REQ13: (parte 2/?) Validación con mensajes explícitos y de uso adaptativo para el usuario
    if (!this.nombre().trim()) {
      validationErrors.nombre = 'El nombre completo es requerido para poder identificarte.';
    }

    const emailValue = this.correo().trim();
    if (!emailValue) {
      validationErrors.correo = 'El correo electrónico es obligatorio para enviarte una respuesta.';
    } else {
      const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
      if (!emailRegex.test(emailValue)) {
        validationErrors.correo = 'El formato del correo no es válido. Ej: usuario@dominio.com';
      }
    }

    const mensajeValue = this.mensaje().trim();
    if (!mensajeValue) {
      validationErrors.mensaje = 'El contenido del mensaje no puede estar vacío.';
    } else if (mensajeValue.length < 20) {
      validationErrors.mensaje = `Por favor, explica un poco más tu consulta (mínimo 20 caracteres, actualmente tienes ${mensajeValue.length}).`;
    }

    this.errors.set(validationErrors);

    if (Object.keys(validationErrors).length === 0) {
      this.sending.set(true);
      const data = {
        nombre: this.nombre().trim(),
        correo: this.correo().trim(),
        mensaje: this.mensaje().trim()
      };

      this.contactoService.enviarMensaje(data).subscribe({
        next: () => {
          this.successMessage.set('¡Tu mensaje ha sido enviado con éxito! Nos pondremos en contacto contigo pronto.');
          this.nombre.set('');
          this.correo.set('');
          this.mensaje.set('');
          this.submitted.set(false);
          this.errors.set({});
          this.sending.set(false);
        },
        error: (err: HttpErrorResponse) => {
          console.error(err);
          this.sending.set(false);
          
          // REQ11 (parte 3/¿?): MANEJO CON EL BACKEND DETENIDO
          if (err.status === 0) {
            this.errorMessage.set(' Error de comunicación: El servidor de soporte (Backend) se encuentra desconectado o bajo mantenimiento. Inténtalo de nuevo cuando el sistema esté en línea.');
          } else {
            this.errorMessage.set('Ocurrió un inconveniente procesando tu solicitud en el servidor. Por favor, intenta de nuevo más tarde.');
          }
        }
      });
    }
  }
}