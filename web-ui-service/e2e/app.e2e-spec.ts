import { WebUiServicePage } from './app.po';

describe('web-ui-service App', () => {
  let page: WebUiServicePage;

  beforeEach(() => {
    page = new WebUiServicePage();
  });

  it('should display welcome message', done => {
    page.navigateTo();
    page.getParagraphText()
      .then(msg => expect(msg).toEqual('Welcome to app!!'))
      .then(done, done.fail);
  });
});
