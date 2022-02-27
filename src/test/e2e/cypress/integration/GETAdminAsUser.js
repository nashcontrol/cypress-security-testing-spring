describe('Authenticated Admin api', () => {
    before(() => {
        cy.login('user'); 
     });
    context('Login and GET /api/admin/me', () => {
        it('should not allow api access for authenticated users not an admin', () => {
            const token = Cypress.env('token');
            const authorization = `bearer ${ token }`;

            cy.request({
                method: 'GET',
                url: 'api/admin/me',
                headers:{
                    authorization:authorization,
                  },
                failOnStatusCode: false
            })
            .should((response) => {
                expect(response.status).to.eq(403)
            });
        });
    });
});