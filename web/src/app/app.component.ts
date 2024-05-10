import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { SseService } from './sse.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent implements OnInit {
  title = 'web';
  progressbar: string;
  constructor(
    private sseService: SseService,
    private httpClient: HttpClient,
    private route: Router
  ) {}

  ngOnInit(): void {}

  listenToEvents() {
    this.sseService.listenToEvent().subscribe(
      (data: any) => {
        if (data.finished) {
          this.progressbar = 'Update over!';
        } else {
          this.progressbar = 'Updated user : ' + data.usersProcessed + '/10';
        }
      },
      (error) => console.log(error)
    );
  }

  operation1() {
    this.httpClient
      .get('http://localhost:8080/ops')
      .subscribe((data) => console.log(data));
  }

  routeMe() {
    this.route.navigate(['route']);
  }
}
