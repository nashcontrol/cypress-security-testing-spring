describe('Authenticated User api', () => {
    before(() => {
        cy.login('user'); 
     });
    context('Login and GET /api/user/me', () => {
        it('should allow api access for all authenticated users', () => {
            const token = Cypress.env('token');
            const authorization = `bearer ${ token }`;

            cy.request({
                method: 'GET',
                url: 'api/user/me',
                headers:{
                    authorization:authorization,
                  }
            })
            .should((response) => {
                expect(response.status).to.eq(200)
                expect(response.body).eq('Hello test')
            });
        });
    });
});