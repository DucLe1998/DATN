import { Component, OnInit } from '@angular/core';
import * as Chartist from 'chartist';

@Component({
  selector: 'statical',
  templateUrl: './statical.component.html',
  styleUrls: ['./statical.component.scss']
})
export class StaticalComponent implements OnInit {

  color = 1 ;

  constructor() { }

  ngOnInit(): void {
  }
}
