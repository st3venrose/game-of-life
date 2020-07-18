import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';

import { environment } from '../../../environments/environment';

export interface IHttpRequest {
  headers?: HttpHeaders;
  observe?: 'body';
  params?: HttpParams;
  responseType?: 'json';
  withCredentials?: boolean;
  body?: any;
}

@Injectable({
  providedIn: 'root',
})
export class ApiService {
  public constructor(public http: HttpClient) {}

  private api = environment.BACKEND_API_URL;

  public get<T>(endPoint: string, options?: IHttpRequest): Observable<T> {
    return this.http.get<T>(`${this.api}/${endPoint}`, options);
  }

  public post<T>(endPoint: string, params?: object, options?: IHttpRequest): Observable<T> {
    return this.http.post<T>(`${this.api}/${endPoint}`, params, options);
  }
}
