import { Injectable, NgZone } from '@angular/core';
import { Observable } from 'rxjs';
import { EventData } from './event-data.model';

@Injectable({
  providedIn: 'root',
})
export class SseService {
  constructor(private zone: NgZone) {}

  listenToEvent() {
    const eventSource = new EventSource(
      'http://localhost:8080/events?userId=1'
    );
    return new Observable((o) => {
      eventSource.addEventListener('NOTIFICATION', (event: MessageEvent) => {
        const eventData: EventData = JSON.parse(event.data) as EventData;
        this.zone.run(() => o.next(eventData));
      });

      eventSource.addEventListener('COMPLETE', (event: MessageEvent) => {
        const eventData: EventData = JSON.parse(event.data) as EventData;
        this.zone.run(() => o.next(eventData));
        eventSource.close();
      });

      eventSource.onerror = (error) => {
        console.log('Error. Closing the event source', error);
        eventSource.close();
        this.zone.run(() => o.error(error));
      };
    });

    // this.eventSource = new EventSource('http://localhost:8080/events?userId=1');
    // console.log('Event Source : ', this.eventSource);
    // return new Observable((o) => {
    //   this.eventSource.addEventListener(
    //     'NOTIFICATION',
    //     (event: MessageEvent) => {
    //       const eventData: EventData = JSON.parse(event.data) as EventData;
    //       this.zone.run(() => o.next(eventData));
    //     }
    //   );

    //   this.eventSource.addEventListener('COMPLETE', (event: MessageEvent) => {
    //     const eventData: EventData = JSON.parse(event.data) as EventData;
    //     this.zone.run(() => o.next(eventData));
    //     console.log('Complete!. Closing the event source');
    //     this.eventSource!.close();
    //     this.eventSource = null;
    //   });

    //   this.eventSource.onerror = (error) => {
    //     console.log('Error. Closing the event source');
    //     this.eventSource!.close();
    //     this.eventSource = null;
    //     this.zone.run(() => o.error(error));
    //   };
    // });
  }
}
