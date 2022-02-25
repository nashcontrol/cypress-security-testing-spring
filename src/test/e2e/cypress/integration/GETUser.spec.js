describe('Authenticated User api', () => {
    context('Login and GET /api/user/me', () => {
        it('should allow api access for all authenticated users', () => {
            cy.login('user')
            
            cy.request({
                method: 'GET',
                url: 'api/user/me'
            })
            .should((response) => {
                expect(response.status).to.eq(200)
                expect(response.body).eq('Hello test')
            });
        });
    });
});